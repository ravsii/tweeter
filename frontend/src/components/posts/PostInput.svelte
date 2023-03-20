<script lang="ts">
  import { createEventDispatcher } from 'svelte'
  import { basePath } from '../../js/api'
  import { userStore } from '../../js/session'
  import { notify } from '../Notifications.svelte'

  const dispatch = createEventDispatcher()

  let value

  async function publishPost() {
    if (!value) {
      return
    }

    const body = JSON.stringify({ content: value })
    const res = await fetch(basePath + '/posts', {
      method: 'POST',
      body: body,
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    const resJson = await res.json()

    if (resJson?.error) {
      notify(resJson?.error, 'error')
      return
    }

    notify('Запись опубликована', 'success')
    dispatch('publish')
    value = null
  }
</script>

{#if $userStore?.id}
  <div class="flex p-4 border rounded glass">
    <div class="input-group grow max-h-48">
      <textarea
        class="textarea textarea-primary grow max-h-48"
        placeholder="Напишите свои мысли"
        rows="1"
        bind:value="{value}"></textarea>
      <button
        class="h-full border-l-0 btn btn-primary hover:text-white"
        on:click="{publishPost}"
      >
        <i class="fa-regular fa-paper-plane"></i>
      </button>
    </div>
  </div>
{/if}
