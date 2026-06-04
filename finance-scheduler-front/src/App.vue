<script setup lang="ts">
	import './assets/css/main.css';
</script>

<template>
	<div class="content-wrapper">
		<nav>
		    <RouterLink to="/new-account">Nova Conta</RouterLink>
		    <RouterLink to="/">Nova Transação</RouterLink>
		    <RouterLink to="/transactions">Lista de Transações</RouterLink>
		</nav>
		<main>
			<header>Finance Scheduler</header>
		    <RouterView />
		</main>
	</div>
</template>

<style scoped>
	.content-wrapper {
		display: flex;
		width: 100%;
		height: 100vh;
	}

	nav {
		background-color: #333333;
		padding: 30px;
		box-sizing: border-box;
		width: 290px;
		display: flex;
		flex-direction: column;
	}

	nav a {
		color: #ffffff;
		text-decoration: none;
	}

	header {
		background-color: cadetblue;
		color: #ffffff;
		padding: 30px;
		width: 100%;
		box-sizing: border-box;
	}

	main {
		width: 100%;
	}
</style>
