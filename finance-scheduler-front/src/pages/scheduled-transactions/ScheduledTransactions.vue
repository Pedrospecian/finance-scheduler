<script setup lang="ts">
	import { ref } from 'vue'
	//todo: api call for fetching transactions
	const items = [
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
	];
</script>

<template>
	<table>
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
			<tr v-for="item in items">
				<td>{{ item.origin }}</td>
				<td>{{ item.destination }}</td>
				<td>{{ item.value }}</td>
				<td>{{ item.transferRate }}</td>
				<td>{{ item.transferDate }}</td>
				<td>{{ item.createdAt }}</td>
			</tr>
		</tbody>
	</table>
</template>

<style scoped>
	
</style>
