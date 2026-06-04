<script setup lang="ts">
	import fntFormatMoney from '../../helpers/fntFormatMoney.ts';
	import fntFormatDate from '../../helpers/fntFormatDate.ts';
	import DataTable from 'primevue/datatable';
	import FormInput from '../../components/FormInput.vue';
	import Column from 'primevue/column';
	import { ref, watch } from 'vue';
	import axios from 'axios';

	const items = ref([]);
	const loaded = ref(false);
	const totalRecords = ref(0);
	const rowsPerPage = ref(10);
	const currentPage = ref(0);
	const searchQuery = ref('');
	const searchCriteria = ref('origin');
	const startDate = ref('');
	const endDate = ref('');

	const fntGetList = () => {
		const isDateCriteria = searchCriteria.value === 'transferDate' || searchCriteria.value === 'createdAt';

		axios.get('http://localhost:8080/api/transactions', {
			params: {
				page: currentPage.value,
				size: rowsPerPage.value,
				query: !isDateCriteria ? searchQuery.value : '',
				criteria: searchCriteria.value,
				startDate: isDateCriteria && startDate.value ? startDate.value : null,
				endDate: isDateCriteria && endDate.value ? endDate.value : null
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

	watch([searchQuery, searchCriteria, startDate, endDate], () => {
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
	<h1 class="title">Lista de Transações</h1>

	<div class="search-container" v-if="loaded">
		<div v-if="['transferDate', 'createdAt'].includes(searchCriteria)" class="date-range-group">
			<input type="date" v-model="startDate" class="date-input" />
			<span>até</span>
			<input type="date" v-model="endDate" class="date-input" />
		</div>
		<input 
			type="text" 
			v-model.lazy="searchQuery" 
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

	<div class="no-items" v-if="loaded && items.length < 1">
		Não há itens para exibir
	</div>
	<div v-if="!loaded">
		Carregando...
	</div>
</template>

<style scoped>
</style>
