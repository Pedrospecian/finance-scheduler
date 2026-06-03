<script setup lang="ts">
	import './assets/css/main.css';
</script>

<template>
	<header>Finance Scheduler</header>
	<!-- <ScheduleForm /> -->
	<nav>
	    <RouterLink to="/">Nova Transação</RouterLink>
	    <RouterLink to="/transactions">Lista de Transações</RouterLink>
	</nav>
	<main>
	    <RouterView />
	</main>
</template>

<style scoped>
	header {
		background-color: cadetblue;
		color: #ffffff;
		padding: 30px;
		width: 100%;
		box-sizing: border-box;
	}
</style>
