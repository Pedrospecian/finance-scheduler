<script setup lang="ts">
	import fntFormatMoney from '../../../helpers/fntFormatMoney.ts';
	import fntFormatDate from '../../../helpers/fntFormatDate.ts';
	import { toast } from 'vue3-toastify';
	import DataTable from 'primevue/datatable';
	import FormInput from '../../../components/FormInput/FormInput.vue';
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

		if (isDateCriteria) {
			let startDateObject = new Date(startDate.value);
			let endDateObject = new Date(endDate.value);

			if (endDateObject.getTime() < startDateObject.getTime()) {
				return toast.error('A data final não pode ser anterior à data inicial.');
			}
		}

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

	/*watch([searchQuery, searchCriteria, startDate, endDate], () => {
		currentPage.value = 0;
		fntGetList();
	});*/

	const fntSetQuickDateRange = (daysAhead: number) => {
		const today = new Date();
		const futureDate = new Date();
		futureDate.setDate(today.getDate() + daysAhead);

		const formatDate = (date: Date) => {
			const year = date.getFullYear();
			const month = String(date.getMonth() + 1).padStart(2, '0');
			const day = String(date.getDate()).padStart(2, '0');
			return `${year}-${month}-${day}`;
		};

		startDate.value = formatDate(today);
		endDate.value = formatDate(futureDate);
		
		fntHandleSearch();
	};

	const onPage = (event: any) => {
		currentPage.value = event.page;
		rowsPerPage.value = event.rows;
		fntGetList();
	};

	const fntSearchReset = () => {
		searchQuery.value = '';
		startDate.value = '';
		endDate.value = '';
		fntGetList();
	}

	const fntHandleSearch = () => {
		currentPage.value = 0;
		fntGetList();
	}

	fntGetList();
</script>

<template>
	<h1 class="title">Lista de Transações</h1>

	<div class="search-container search-container-transactions" v-if="loaded">
		<div class="search-container-main-fields">
			<div v-if="['transferDate', 'createdAt'].includes(searchCriteria)" class="date-range-group">
				<input type="date" v-model.lazy="startDate" class="date-input" />
				<span>até</span>
				<input type="date" v-model.lazy="endDate" class="date-input" />
			</div>
			
			<input 
				type="text" 
				v-model.lazy="searchQuery" 
				placeholder="Buscar" 
				class="search-input"
				v-else
			/>

			<select v-model="searchCriteria" class="search-select">
				<option value="origin">Conta de Origem</option>
				<option value="destination">Conta de Destino</option>
				<option value="transferDate">Data de Transferência</option>
				<option value="createdAt">Data de Agendamento</option>
			</select>
			
			<button class="button-search" @click="fntHandleSearch">
				Buscar
			</button>
			<button class="button-reset" @click="fntSearchReset">
				Limpar Busca
			</button>
		</div>
		<div class="quick-date-buttons" v-if="['transferDate', 'createdAt'].includes(searchCriteria)">
			<button type="button" class="button-quick" @click="fntSetQuickDateRange(7)">
				Próximos 7 dias
			</button>
			<button type="button" class="button-quick" @click="fntSetQuickDateRange(14)">
				Próximos 14 dias
			</button>
		</div>
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
	.quick-date-buttons {
		display: flex;
		gap: 5px;
	}
	.button-quick {
		background-color: #f1f5f9;
		color: #475569;
		border: 1px solid #cbd5e1;
		padding: 6px 12px;
		font-size: 14px;
		border-radius: 4px;
		cursor: pointer;
		transition: all 0.2s;
	}

	.button-quick:hover {
		background-color: #e2e8f0;
		color: #1e293b;
		border-color: #94a3b8;
	}

	.search-container.search-container-transactions {
		flex-direction: column;
	}

	.search-container-main-fields {
		display: flex;
		gap: 18px;
		max-width: 600px;
		width: 100%;
		box-sizing: border-box;
	}
</style>
