import { createMemoryHistory, createRouter } from 'vue-router';

import HomePage from './modules/home/HomePage.vue';

import RegisterTransaction from './modules/transactions/register/RegisterTransaction.vue';
import TransactionsList from './modules/transactions/list/TransactionsList.vue';

import AccountsRegister from './modules/accounts/register/AccountsRegister.vue';
import AccountsList from './modules/accounts/list/AccountsList.vue';

const routes = [
	{ path: '/', component: HomePage },

	{ path: '/new-transaction', component: RegisterTransaction },
	{ path: '/transactions', component: TransactionsList },

	{ path: '/new-account', component: AccountsRegister },
	{ path: '/accounts', component: AccountsList },
]

export const router = createRouter({
	history: createMemoryHistory(),
	routes,
})