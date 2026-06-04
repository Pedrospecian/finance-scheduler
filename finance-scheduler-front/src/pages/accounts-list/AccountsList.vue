<script setup lang="ts">
	import fntFormatMoney from '../../helpers/fntFormatMoney.ts';
	import DataTable from 'primevue/datatable';
	import Column from 'primevue/column';
	import { ref } from 'vue';
	import axios from 'axios';

	const items = ref([]);
	const loaded = ref(false);

	axios.get('http://localhost:8080/api/accounts').then((response) => {
	  items.value = response.data;
	  loaded.value = true;
	}).catch((err) => {
		console.log(err);
		items.value = [];
		loaded.value = true;
	});
</script>

<template>
	<h1 class="title">Lista de Contas</h1>

	<DataTable v-if="loaded && items.length >= 1" :value="items" tableStyle="min-width: 50rem">
	    <Column field="id" header="#"></Column>
	    <Column field="accountNumber" header="Número da Conta"></Column>
	    <Column field="balance" header="Saldo Inicial"></Column>
	</DataTable>

	<div v-if="loaded && items.length < 1">
		Não há itens para exibir
	</div>
	<div v-if="!loaded">
		Carregando...
	</div>
</template>

<style scoped>
	
</style>
