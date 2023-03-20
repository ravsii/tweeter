<script lang="ts">
  import { goto, url } from '@roxi/routify'
  import { onMount } from 'svelte'
  import { notify } from '../../components/Notifications.svelte'
  import { basePath } from '../../js/api'
  import { userStore } from '../../js/session'

  export let error: Function

  interface LoginData {
    login: string
    password: string
  }

  let data: LoginData = {
    login: '',
    password: '',
  }

  async function loginRequest() {
    const body = JSON.stringify({
      name: data.login,
      password: data.password,
    })

    const res = await fetch(basePath + '/user/login', {
      method: 'POST',
      body: body,
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    const resJson = await res.json()

    if (resJson?.error) {
      error(resJson?.error)
      return
    }

    userStore.set(resJson)
    notify('Вы вошли', 'success')
    $goto('/')
  }

  function handleLogin() {
    if (!data?.password) {
      error('Вы не заполнили поле пароль.')
      return
    }

    if (!data?.login) {
      error('Вы не заполнили поле логин.')
      return
    }

    loginRequest()
  }

  onMount(() => {
    error = $$props.scoped.error
  })
</script>

<div class="flex flex-col gap-4">
  <div class="form-control">
    <label class="label" for="login">
      <span class="label-text">Логин</span>
    </label>
    <input
      type="text"
      id="login"
      class="w-full input input-bordered input-sm focus:input-primary"
      bind:value="{data.login}"
    />
  </div>
  <div class="form-control">
    <label class="label" for="password">
      <span class="label-text">Пароль</span>
    </label>
    <input
      type="password"
      id="password"
      class="w-full input input-bordered input-sm focus:input-primary"
      bind:value="{data.password}"
    />
  </div>
  <button class="btn btn-block btn-primary" on:click="{handleLogin}">
    Войти
  </button>
  <span class="text-sm">
    Нет аккаунта? <a href="{$url('./register')}" class="font-bold link">
      Зарегистрируйтесь здесь
    </a>
  </span>
</div>
