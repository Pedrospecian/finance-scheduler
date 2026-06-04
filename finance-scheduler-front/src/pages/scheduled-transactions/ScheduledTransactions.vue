<script setup lang="ts">
	import fntFormatMoney from '../../helpers/fntFormatMoney.ts';
	import fntFormatDate from '../../helpers/fntFormatDate.ts';
	import DataTable from 'primevue/datatable';
	import FormInput from '../../components/FormInput.vue';
	import Column from 'primevue/column';
	import { ref, computed } from 'vue';
	import axios from 'axios';

	const items = ref([]);
	const loaded = ref(false);
	const searchQuery = ref('');
	const searchCriteria = ref('origin');

	axios.get('http://localhost:8080/api/transactions').then((response) => {
	  items.value = response.data;
	  loaded.value = true;
	}).catch((err) => {
		console.log(err);
		items.value = [];
		loaded.value = true;
	});

	const fntSearchQueryReset = () => {
		searchQuery.value = '';
	}

	const filteredItems = computed(() => {
		const query = searchQuery.value.trim().toLowerCase();
		
		if (!query) return items.value;

		return items.value.filter((item: any) => {
			const originMatch = item.origin?.accountNumber?.toLowerCase().includes(query);
			const destinationMatch = item.destination?.accountNumber?.toLowerCase().includes(query);
			const transferDateMatch = item.transferDate?.includes(query);
			const createdAtMatch = item.createdAt?.includes(query);
			
			// Switch behavior based on the chosen criteria selection
			if (searchCriteria.value === 'origin') {
				return originMatch;
			} else if (searchCriteria.value === 'destination') {
				return destinationMatch;
			} else if (searchCriteria.value === 'transferDate') {
				return transferDateMatch;
			} else {
				return createdAtMatch;
			}
		});
	});
</script>

<template>
	<h1 class="title">Lista de Transações</h1>

	<div class="search-container" v-if="loaded">
		<input 
			type="date" 
			v-model="searchQuery" 
			placeholder="Buscar" 
			class="search-input"
			v-if="['transferDate', 'createdAt'].includes(searchCriteria)"
		/>
		<input 
			type="text" 
			v-model="searchQuery" 
			placeholder="Buscar" 
			class="search-input"
			v-else
		/>
		<select v-model="searchCriteria" class="search-select" @change="fntSearchQueryReset">
			<option value="origin">Conta de Origem</option>
			<option value="destination">Conta de Destino</option>
			<option value="transferDate">Data de Transferência</option>
			<option value="createdAt">Data de Agendamento</option>
		</select>
	</div>

	<DataTable v-if="loaded && filteredItems.length >= 1" :value="filteredItems" tableStyle="min-width: 50rem">
	    <Column header="Conta de Origem">
	    	<template #body="item">
	            {{ item.data.origin.accountNumber }}
	        </template>
	    </Column>
	    <Column header="Conta de Destino">
	    	<template #body="item">
	            {{ item.data.destination.accountNumber }}
	        </template>
	    </Column>
	    <Column header="Valor">
	    	<template #body="item">
	            {{ fntFormatMoney(item.data.value) }}
	        </template>
	    </Column>
	    <Column header="Taxa de Transferência">
	    	<template #body="item">
	            {{ fntFormatMoney(item.data.transferRate) }}
	        </template>
	    </Column>
	    <Column header="Data de Transferência">
	    	<template #body="item">
	    		{{ fntFormatDate(item.data.transferDate) }}
	    	</template>	    	
	    </Column>
	    <Column header="Data de Agendamento">
	    	<template #body="item">
	    		{{ fntFormatDate(item.data.createdAt) }}
	    	</template>
	    </Column>
	</DataTable>

	<div v-if="loaded && filteredItems.length < 1">
		Não há itens para exibir
	</div>
	<div v-if="!loaded">
		Carregando...
	</div>
</template>

<style scoped>
	.search-container {
		display: flex;
		gap: 18px;
		margin-bottom: 12px;
		max-width: 600px;
		padding: 24px 12px;
		width: 100%;
		box-sizing: border-box;
	}
	.search-select {
		padding: 12px;
		font-size: 14px;
		border: 1px solid #ccc;
		border-radius: 6px;
		background-color: white;
		cursor: pointer;
	}
	.search-input {
		flex: 1;
		padding: 12px;
		font-size: 14px;
		border: 1px solid #ccc;
		border-radius: 6px;
		box-sizing: border-box;
	}
	.search-input:focus, .search-select:focus {
		outline: none;
		border-color: #3b82f6;
		box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.2);
	}
</style>
