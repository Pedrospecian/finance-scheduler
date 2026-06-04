<script setup lang="ts">
	import fntFormatMoney from '../../helpers/fntFormatMoney.ts';
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
	<h1 class="title">Página inicial</h1>

	<p>
		Seja bem vindo ao Finance Scheduler.
	</p>
</template>

<style scoped>
	
</style>
