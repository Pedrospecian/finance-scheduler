<script setup lang="ts">
	import InputMask from 'primevue/inputmask';
	import InputNumber from 'primevue/inputnumber';

	defineProps(['title', 'id', 'fieldType', 'mask', 'placeholder', 'modelValue'])
</script>

<template>
	<div>
		<label :for="id">{{ title }}</label>

		<div v-if="fieldType === 'currency'">
			<InputNumber 
				:id="id"
				:modelValue="modelValue"
				@update:modelValue="$emit('update:modelValue', $event)"
				mode="currency" 
				currency="BRL" 
				locale="pt-BR" 
				:placeholder="placeholder"
			/>
		</div>

		<div v-else-if="mask">
			<InputMask 
				:mask="mask" 
				:id="id" 
				:modelValue="modelValue"
				@update:modelValue="$emit('update:modelValue', $event)"
				:placeholder="placeholder" 
			/>
		</div>
		
		<div v-else>
			<input 
				:type="fieldType" 
				:id="id" 
				:value="modelValue"
				@input="$emit('update:modelValue', ($event.target as HTMLInputElement).value)"
				:placeholder="placeholder"
			>
		</div>
	</div>
</template>

<style scoped>
	div {
		margin-bottom: 12px;
		display: flex;
		flex-direction: column;
	}
	input {
		font-size: 14px;
		padding: 12px 8px;
		border-radius: 6px;
		border: solid 1px #cbd5e1;
	}
</style>