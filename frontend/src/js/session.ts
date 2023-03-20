import { get, writable } from 'svelte/store'
import { basePath } from './api'

interface UserInterface {
  id: string
  name: string
}

export const userStore = writable<UserInterface | any>({})

export async function updateSession() {
  const user = get<UserInterface | any>(userStore)
  if (user?.id) {
    return
  }

  console.log('Updating session')

  const res = await fetch(basePath + '/session', {
    credentials: 'include',
  })
  const json = await res.json()

  if (!json?.name) {
    return
  }

  userStore.set(json)
}

export async function logout() {
  userStore.set({})
}
