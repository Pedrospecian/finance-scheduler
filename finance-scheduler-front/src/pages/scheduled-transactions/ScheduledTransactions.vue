<script setup lang="ts">
	import { ref } from 'vue'
	import axios from 'axios'

	const items = ref([
		{
			origin: '123456789',
			destination: '453456789',
			value: 15.00,
			transferRate: 2.5,
			transferDate: '2026-06-13',
			createdAt: '2026-06-03'
		},
		{
			origin: '453456789',
			destination: '123456789',
			value: 12.00,
			transferRate: 1.5,
			transferDate: '2026-06-15',
			createdAt: '2026-06-03'
		},
	]);
	const loaded = ref(false);

	//todo: api call for fetching transactions
	axios.get('http://localhost:8080/api/transactions').then((response) => {
	  items.value = response.data;
	  loaded.value = true;
	}).catch((err) => {
		console.log(err);
		items.value = [];
		loaded.value = true;
	});
</script>

<template>
	<table v-if="loaded && items.length >= 1">
		<thead>
			<tr>
		    	<th>Conta Origem</th>
		    	<th>Conta Destino</th>
		    	<th>Valor</th>
		    	<th>Taxa de Transferência</th>
		    	<th>Data de Transferência</th>
		    	<th>Data de Agendamento</th>
		    </tr>
		</thead>
		<tbody>
			<tr v-for="item in items" :key="item.origin + item.createdAt">
				<td>{{ item.origin }}</td>
				<td>{{ item.destination }}</td>
				<td>{{ item.value }}</td>
				<td>{{ item.transferRate }}</td>
				<td>{{ item.transferDate }}</td>
				<td>{{ item.createdAt }}</td>
			</tr>
		</tbody>
	</table>
	<div v-if="loaded && items.length < 1">
		Não há itens para exibir
	</div>
	<div v-if="!loaded">
		Carregando...
	</div>
</template>

<style scoped>
	
</style>
