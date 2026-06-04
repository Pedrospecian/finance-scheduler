<script setup lang="ts">
	import fntFormatMoney from '../../../helpers/fntFormatMoney.ts';
	import DataTable from 'primevue/datatable';
	import Column from 'primevue/column';
	import { ref, watch } from 'vue';
	import axios from 'axios';

	const items = ref([]);
	const loaded = ref(false);
	const totalRecords = ref(0);
	const rowsPerPage = ref(10);
	const currentPage = ref(0);
	const searchQuery = ref('');
	const searchCriteria = ref('accountNumber');

	const fntGetList = () => {
		axios.get('http://localhost:8080/api/accounts', {
			params: {
				page: currentPage.value,
				size: rowsPerPage.value,
				query: searchQuery.value,
				criteria: searchCriteria.value,
			}
		}).then((response) => {
			items.value = response.data.content;
			totalRecords.value = response.data.totalElements;
			loaded.value = true;
		}).catch((err) => {
			console.log(err);
			items.value = [];
			loaded.value = true;
		});
	}

	watch([searchQuery, searchCriteria], () => {
		currentPage.value = 0;
		fntGetList();
	});

	const onPage = (event: any) => {
		currentPage.value = event.page;
		rowsPerPage.value = event.rows;
		fntGetList();
	};

	const fntSearchQueryReset = () => {
		searchQuery.value = '';
	}

	fntGetList();
</script>

<template>
	<h1 class="title">Lista de Contas</h1>

	<div class="search-container" v-if="loaded">
		<input 
			type="text" 
			v-model.lazy="searchQuery" 
			placeholder="Buscar" 
			class="search-input"
		/>
		<select v-model="searchCriteria" class="search-select" @change="fntSearchQueryReset">
			<option value="accountNumber">Número da Conta</option>
		</select>
	</div>

	<DataTable
		v-if="loaded && items.length >= 1" 
		:value="items" 
		lazy 
		paginator 
		:rows="rowsPerPage" 
		:totalRecords="totalRecords" 
		@page="onPage"
		tableStyle="min-width: 800px"
	>
	    <Column field="id" header="#"></Column>
	    <Column field="accountNumber" header="Número da Conta"></Column>
	    <Column header="Saldo Inicial">
	    	<template #body="item">
	            {{ fntFormatMoney(item.data.balance) }}
	        </template>
	    </Column>
	</DataTable>

	<div class="no-items" v-if="loaded && items.length < 1">
		Não há itens para exibir
	</div>
	<div v-if="!loaded">
		Carregando...
	</div>
</template>

<style scoped>
	
</style>
