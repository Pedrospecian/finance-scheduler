<script setup lang="ts">
	import FormInput from '../../components/FormInput.vue';
	import fntFormatMoney from '../../helpers/fntFormatMoney.ts';
	import { toast } from 'vue3-toastify';
	import 'vue3-toastify/dist/index.css';
	import { ref } from 'vue';
	import axios from 'axios';
	import { router } from '../../router.ts';

	const conta = ref('')
	const saldoInicial = ref(0)
	const isSubmitting = ref(false);

	const fntIsFormValid = () => {
		if (!conta._value || saldoInicial._value === '') {
			return false;
		}

		return true;
	}

	const fntCadastrar = () => {
		if (!fntIsFormValid()) {
			return;
		}

		isSubmitting.value = true;

		const obj = {
			accountNumber: conta.value,
			balance: saldoInicial.value,
		}

		axios.post('http://localhost:8080/api/accounts', obj).then((response) => {
			toast.success('Conta criada com sucesso!');
			setTimeout(() => {
				router.push('/accounts');
			}, 1000);
		}).catch((err) => {
			console.log('err-->', err.response);
			toast.error(err.response.data.error);
			isSubmitting.value = false;
		});
	}

	const fntDisableSubmit = () => {
		return isSubmitting.value || !fntIsFormValid();
	}

</script>

<template>
	<h1 class="title">Nova Conta</h1>
	<form action="" class="form-box">
		<FormInput
			title="Número da conta"
			id="conta"
			mask="9999999999"
			placeholder="XXXXXXXXXX"
			fieldType="text"
			v-model="conta"
			modelValue="conta"
		/>
		<FormInput
			title="Saldo inicial"
			id="saldoInicial"
			fieldType="number"
			v-model="saldoInicial"
			modelValue="saldoInicial"
		/>

		<button
			type="button"
			@click="fntCadastrar"
			:disabled="fntDisableSubmit()"
			v-bind:class="{'button-submit': true, 'disabled': fntDisableSubmit()}"
		>
			Cadastrar
		</button>
	</form>
</template>

<style scoped>
</style>
