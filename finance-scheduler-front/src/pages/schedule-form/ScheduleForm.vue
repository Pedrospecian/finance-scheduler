<script setup lang="ts">
	import FormInput from '../../components/FormInput.vue'
	import { ref } from 'vue'

	const contaOrigem = ref('')
	const contaDestino = ref('')
	const valorTransferencia = ref('')
	const dataTransferencia = ref('')
	const errorMessage = ref('');

	const taxaTransferencia = () => {
		if (dataTransferencia._value && valorTransferencia._value) {
			const today = new Date();
			const transferDate = new Date(dataTransferencia._value);

			const diffTime = transferDate - today;
			const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24)) + 1;

			console.log(diffDays);

			if (diffDays < 0) {
				errorMessage._value = 'A data escolhida não pode ser anterior à data atual. Por favor, escolha outra data.'
				return 'ERRO';
			}

			if (diffDays < 1) {
				errorMessage._value = '';
				return 3.0 + valorTransferencia._value * 0.025;
			} else if (diffDays <= 10) {
				errorMessage._value = '';
				return 12.0 + valorTransferencia._value * 0.0;
			} else if (diffDays <= 20) {
				errorMessage._value = '';
				return valorTransferencia._value * 0.082;
			} else if (diffDays <= 30) {
				errorMessage._value = '';
				return valorTransferencia._value * 0.069;
			} else if (diffDays <= 40) {
				errorMessage._value = '';
				return valorTransferencia._value * 0.047;
			} else if (diffDays <= 50) {
				errorMessage._value = '';
				return valorTransferencia._value * 0.017;
			} else {
				errorMessage._value = 'Não há taxa aplicável para a data selecionada. Por favor, escolha outra data.'
				return 'ERRO';
			}
		} else {
			errorMessage._value = 'Por favor, informe a data e o valor da transferência.'
			return 'ERRO';
		}
	}

	//todo: api call for getting transfer rate

	//todo: api call for submitting form

	const fntValidTaxaTransferencia = () => {
		return taxaTransferencia() !== 'ERRO';
	}

	const fntFormatPrice = (price) => {
		return price.toLocaleString('pt-br', {style: 'currency', currency: 'BRL'});
	}

	const fntTransferir = () => {
		console.log(contaOrigem._value, contaDestino._value, valorTransferencia._value, dataTransferencia._value);
		return null;
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
		/>

		<div v-if="fntValidTaxaTransferencia()" class="subtotal">Taxa de transferência: {{ fntFormatPrice(taxaTransferencia()) }}</div>
		<div v-if="!fntValidTaxaTransferencia() && errorMessage" class="feedback-error">{{ errorMessage }}</div>
		<button type="button" @click="fntTransferir()">Transferir</button>
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
</style>
