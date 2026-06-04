<script setup lang="ts">
	import fntFormatMoney from '../../helpers/fntFormatMoney.ts';
	import fntFormatDate from '../../helpers/fntFormatDate.ts';
	import DataTable from 'primevue/datatable';
	import Column from 'primevue/column';
	import { ref } from 'vue';
	import axios from 'axios';

	const items = ref([]);
	const loaded = ref(false);

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
	<h1 class="title">Lista de Transações</h1>

	<DataTable v-if="loaded && items.length >= 1" :value="items" tableStyle="min-width: 50rem">
	    <Column header="Conta Origem">
	    	<template #body="item">
	            {{ item.data.origin.accountNumber }}
	        </template>
	    </Column>
	    <Column header="Conta Destino">
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

	<div v-if="loaded && items.length < 1">
		Não há itens para exibir
	</div>
	<div v-if="!loaded">
		Carregando...
	</div>
</template>

<style scoped>
	
</style>
