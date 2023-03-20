<script lang="ts">
  import { goto, url } from '@roxi/routify'
  import { onMount } from 'svelte'
  import { notify } from '../../components/Notifications.svelte'
  import { basePath } from '../../js/api'
  import { userStore } from '../../js/session'

  export let error: Function

  interface RegisterData {
    login: string
    password: string
    repeatPassword: string
    check: boolean
  }

  let data: RegisterData = {
    login: '',
    password: '',
    repeatPassword: '',
    check: false,
  }

  async function registrationRequest() {
    const body = JSON.stringify({
      name: data.login,
      password: data.password,
    })

    const res = await fetch(basePath + '/user/register', {
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
    notify('Вы зарегистрированы', 'success')
    $goto('/')
  }

  function handleRegister() {
    if (!data?.check) {
      error('Вы не отметили галку.')
      return
    }

    if (!data?.password) {
      error('Вы не заполнили поле пароль.')
      return
    }

    if (!data?.login) {
      error('Вы не заполнили поле логин.')
      return
    }

    if (data?.password != data?.repeatPassword) {
      error('Пароли не совпадают.')
      return
    }

    registrationRequest()
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
  <div class="form-control">
    <label class="label" for="repeatPassword">
      <span class="label-text">Повторите пароль</span>
    </label>
    <input
      type="password"
      id="repeatPassword"
      class="w-full input input-bordered input-sm focus:input-primary"
      bind:value="{data.repeatPassword}"
    />
  </div>
  <div class="flex-row gap-2 form-control place-items-center">
    <input
      type="checkbox"
      class="checkbox checkbox-primary"
      id="check"
      bind:checked="{data.check}"
    />
    <label class="cursor-pointer label" for="check">
      <span class="label-text">Я модная галка</span>
    </label>
  </div>
  <button class="btn btn-block btn-primary" on:click="{handleRegister}"
    >Зарегистрироваться</button
  >
  <span class="text-sm">
    Уже зарегистрированы? <a href="{$url('./login')}" class="font-bold link"
      >Войдите здесь</a
    >
  </span>
</div>
