<script setup lang="ts">
	import './assets/css/main.css';
</script>

<template>
	<div class="content-wrapper">
		<nav>
			<ul>
				<li>Contas</li>
			    <li><RouterLink to="/new-account">Nova Conta</RouterLink></li>
			    <li><RouterLink to="/accounts">Contas</RouterLink></li>
			</ul>
			
		    <ul>
		    	<li>Transações</li>
			    <li><RouterLink to="/">Nova Transação</RouterLink></li>
			    <li><RouterLink to="/transactions">Lista de Transações</RouterLink></li>
		    </ul>
		    
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
		color: #ffffff;
		padding: 30px;
		box-sizing: border-box;
		width: 290px;
		display: flex;
		flex-direction: column;
	}

	nav ul {
		list-style: none;
		padding-left: 0px;
		margin-bottom: 0px;
	}

	nav li {
		margin-bottom: 12px;
	}

	nav a {
		color: #ffffff;
		text-decoration: none;
		margin-left: 12px;
	}

	header {
		background-color: #2ea8cc;
		color: #ffffff;
		padding: 30px;
		width: 100%;
		box-sizing: border-box;
	}

	main {
		width: 100%;
		height: 100vh;
		overflow-y: auto;
	}
</style>
