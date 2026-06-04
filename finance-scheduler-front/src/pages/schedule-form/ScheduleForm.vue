<script setup lang="ts">
	import FormInput from '../../components/FormInput.vue'
	import fntFormatMoney from '../../helpers/fntFormatMoney.ts'
	import { toast } from 'vue3-toastify';
	import 'vue3-toastify/dist/index.css';
	import { ref } from 'vue'
	import axios from 'axios'

	const contaOrigem = ref('')
	const contaDestino = ref('')
	const valorTransferencia = ref('')
	const dataTransferencia = ref('')
	const errorMessage = ref('');
	const valorTaxa = ref('');
	const statusCalculoValorTaxa = ref(true);
	const isSubmitting = ref(false);

	//todo: api call for getting transfer rate
	const calcularTaxa = async () => {
		axios.get(`http://localhost:8080/api/transactions/utils/calcular-taxa?transferDate=${dataTransferencia._value}&value=${valorTransferencia._value}`).then((response) => {
		  console.log('response--->', response);
		  valorTaxa.value = response.data.taxa;
		  statusCalculoValorTaxa.value = true;
		}).catch((err) => {
			console.log('err--->', err, err.response.data.error);
			if (err.response.data.error === 'Bad Request') {
				errorMessage.value = 'Por favor, informe a data e o valor da transferência.';
			} else {
				errorMessage.value = err.response.data.error;
			}
			statusCalculoValorTaxa.value = false;
		});
	}

	const fntIsFormValid = () => {
		if (!contaOrigem._value || !contaDestino._value || !valorTransferencia._value || !dataTransferencia.value) {
			return false;
		}

		return true;
	}

	const fntTransferir = () => {
		console.log('treste');
		if (!fntIsFormValid()) {
			statusCalculoValorTaxa.value = false;
			return;
		}
		errorMessage.value = '';
		console.log('treste-->', contaOrigem._value, contaDestino._value, valorTransferencia._value, dataTransferencia._value);
		isSubmitting.value = true;

		const obj = {
			origin: contaOrigem._value,
			destination: contaDestino._value,
			transferDate: dataTransferencia.value,
			value: valorTransferencia.value,
		}

		axios.post('http://localhost:8080/api/transactions', obj).then((response) => {
			console.log('submitrespoonse--->', response);
			isSubmitting.value = false;
		}).catch((err) => {
			console.log('submiter--->', err);
			toast.error(err.response.data.error);
			isSubmitting.value = false;
		});
	}

	const fntDisableSubmit = () => {
		return isSubmitting.value || !fntIsFormValid() || (!statusCalculoValorTaxa && errorMessage);
	}

</script>

<template>
	<form action="">
		<FormInput
			title="Conta de origem"
			id="contaOrigem"
			mask="9999999999"
			placeholder="XXXXXXXXXX"
			fieldType="text"
			v-model="contaOrigem"
			modelValue="contaOrigem"
		/>
		<FormInput
			title="Conta de destino"
			id="contaDestino"
			mask="9999999999"
			placeholder="XXXXXXXXXX"
			fieldType="text"
			v-model="contaDestino"
			modelValue="contaDestino"
		/>
		<FormInput
			title="Valor da transferência"
			id="valorTransferencia"
			fieldType="currency"
			v-model="valorTransferencia"
			modelValue="valorTransferencia"
		/>
		<FormInput
			title="Data da transferência"
			id="dataTransferencia"
			fieldType="date"
			v-model="dataTransferencia"
			modelValue="dataTransferencia"
			@change="calcularTaxa()"
		/>

		<div v-if="statusCalculoValorTaxa" class="subtotal">Taxa de transferência: {{ fntFormatMoney(valorTaxa) }}</div>
		<div v-if="!statusCalculoValorTaxa && errorMessage" class="feedback-error">{{ errorMessage }}</div>
		<button
			type="button"
			@click="fntTransferir"
			:disabled="fntDisableSubmit()"
			v-bind:class="{'disabled': fntDisableSubmit()}"
		>
			Transferir
		</button>
	</form>
</template>

<style scoped>
	form {
		display: flex;
		flex-direction: column;
		box-sizing: border-box;
		margin-left: auto;
		margin-right: auto;
		padding: 24px;
		width: calc(100% - 30px);
		max-width: 600px;
		border-radius: 6px;
		-webkit-box-shadow: 3px 3px 15px -4px rgba(0,0,0,0.5); 
		box-shadow: 3px 3px 15px -4px rgba(0,0,0,0.5);
	}

	.subtotal {
		margin-bottom: 12px;
	}

	.feedback-error {
		padding: 12px;
		border-radius: 6px;
		color: #ffffff;
		background-color: #ff9999;
		text-align: center;
		margin-top: 12px;
		margin-bottom: 12px;
	}

	button {
		background-color: cadetblue;
		color: #ffffff;
		border: none;
		border-radius: 6px;
		padding: 12px;
		width: 100%;
		cursor: pointer;
	}

	button.disabled {
		opacity: 0.5;
		cursor: default;
	}
</style>
