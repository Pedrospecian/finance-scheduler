import { createMemoryHistory, createRouter } from 'vue-router';

import ScheduleForm from './pages/schedule-form/ScheduleForm.vue';
import ScheduledTransactions from './pages/scheduled-transactions/ScheduledTransactions.vue';
import NewAccountForm from './pages/new-account-form/NewAccountForm.vue';
import AccountsList from './pages/accounts-list/AccountsList.vue';

const routes = [
  { path: '/', component: ScheduleForm },
  { path: '/transactions', component: ScheduledTransactions },
  { path: '/new-account', component: NewAccountForm },
  { path: '/accounts', component: AccountsList },
]

export const router = createRouter({
  history: createMemoryHistory(),
  routes,
})