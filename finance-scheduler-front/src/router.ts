import { createMemoryHistory, createRouter } from 'vue-router'

import ScheduleForm from './pages/schedule-form/ScheduleForm.vue'
import ScheduledTransactions from './pages/scheduled-transactions/ScheduledTransactions.vue'

const routes = [
  { path: '/', component: ScheduleForm },
  { path: '/transactions', component: ScheduledTransactions },
]

export const router = createRouter({
  history: createMemoryHistory(),
  routes,
})