<script setup lang="ts">
</script>

<template>
	<nav>
		<ul>
			<li><RouterLink to="/">Página Inicial</RouterLink></li>
		</ul>
		<ul>
			<li>Contas</li>
		    <li><RouterLink to="/accounts">Listagem</RouterLink></li>
		    <li><RouterLink to="/new-account">Nova Conta</RouterLink></li>
		</ul>
		
	    <ul>
	    	<li>Transações</li>
		    <li><RouterLink to="/transactions">Listagem</RouterLink></li>
		    <li><RouterLink to="/new-transaction">Nova Transação</RouterLink></li>
	    </ul>
	    
	</nav>
</template>

<style scoped>
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
</style>