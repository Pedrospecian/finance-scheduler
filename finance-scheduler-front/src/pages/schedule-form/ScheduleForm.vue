<script setup lang="ts">
	import FormInput from '../../components/FormInput.vue';
	import fntFormatMoney from '../../helpers/fntFormatMoney.ts';
	import { toast } from 'vue3-toastify';
	import 'vue3-toastify/dist/index.css';
	import { ref } from 'vue';
	import axios from 'axios';
	import { router } from '../../router.ts';

	const contaOrigem = ref('')
	const contaDestino = ref('')
	const valorTransferencia = ref('')
	const dataTransferencia = ref('')
	const valorTaxa = ref('');
	const statusCalculoValorTaxa = ref(true);
	const isSubmitting = ref(false);

	const calcularTaxa = async () => {
		axios.get(`http://localhost:8080/api/transactions/utils/calcular-taxa?transferDate=${dataTransferencia._value}&value=${valorTransferencia._value}`).then((response) => {
		  valorTaxa.value = response.data.taxa;
		  statusCalculoValorTaxa.value = true;
		}).catch((err) => {
			if (err.response.data.error === 'Bad Request') {
				toast.error('Por favor, informe a data e o valor da transferência.');
			} else {
				toast.error(err.response.data.error);
			}
			valorTaxa.value = '';
			statusCalculoValorTaxa.value = false;
		});
	}

	const fntIsFormValid = () => {
		if (!contaOrigem._value || !contaDestino._value || !valorTransferencia._value || !dataTransferencia.value) {
			return false;
		}

		return true;
	}

	const fntValidateTransferDate = () => {
	    let start = new Date();
	    let end = new Date(dataTransferencia.value);
	    let timeDiff = end - start;
	    return Math.ceil((timeDiff / (1000 * 3600 * 24)) + 1) >= 0;
	}

	const fntTransferir = () => {
		if (!fntIsFormValid()) {
			return;
		}

		if (contaOrigem._value === contaDestino._value) {
			return toast.error('A conta de destino não pode ser a mesma que a conta de origem');
		}

		if (!fntValidateTransferDate()) {
			return toast.error('A data escolhida não pode ser anterior à data atual. Por favor, escolha outra data.');
		}

		isSubmitting.value = true;

		const obj = {
			origin: contaOrigem._value,
			destination: contaDestino._value,
			transferDate: dataTransferencia.value,
			value: valorTransferencia.value,
		}

		axios.post('http://localhost:8080/api/transactions', obj).then((response) => {
			toast.success('Transação efetuada com sucesso!');
			setTimeout(() => {
				router.push('/transactions');
			}, 1000);
		}).catch((err) => {
			toast.error(err.response.data.error);
			isSubmitting.value = false;
		});
	}

	const fntDisableSubmit = () => {
		return isSubmitting.value || !fntIsFormValid() || (!statusCalculoValorTaxa);
	}

</script>

<template>
	<h1 class="title">Nova Transação</h1>
	<form action="" class="form-box">
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
			placeholder="R$ 0,00"
		/>
		<FormInput
			title="Data da transferência"
			id="dataTransferencia"
			fieldType="date"
			v-model="dataTransferencia"
			modelValue="dataTransferencia"
			@change="calcularTaxa()"
		/>

		<div v-if="valorTaxa" class="subtotal">Taxa de transferência: {{ fntFormatMoney(valorTaxa) }}</div>
		<button
			type="button"
			@click="fntTransferir"
			:disabled="fntDisableSubmit()"
			v-bind:class="{'button-submit': true, 'disabled': fntDisableSubmit()}"
		>
			Transferir
		</button>
	</form>
</template>

<style scoped>
</style>
