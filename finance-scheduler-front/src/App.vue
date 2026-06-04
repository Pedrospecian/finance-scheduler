<script setup lang="ts">
	import './assets/css/main.css';
	import Sidebar from './components/Sidebar/Sidebar.vue';
	import Header from './components/Header/Header.vue';
</script>

<template>
	<div class="content-wrapper">
		<Sidebar />
		<main>
			<Header />
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

	main {
		width: 100%;
		height: 100vh;
		overflow-y: auto;
	}
</style>
