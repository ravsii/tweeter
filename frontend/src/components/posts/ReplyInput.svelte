<script lang="ts">
  import { createEventDispatcher } from 'svelte'
  import { basePath } from '../../js/api'
  import { userStore } from '../../js/session'
  import { notify } from '../Notifications.svelte'
  import ProfilePic from '../user/ProfilePic.svelte'

  export let parentPost
  let value
  const dispatch = createEventDispatcher()

  async function publishPost() {
    if (!value) {
      return
    }

    const body = JSON.stringify({
      content: value,
      replyTo: { id: parentPost.id },
    })

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

    notify('Ответ опубликован', 'success')
    dispatch('publish')
    value = null
  }
</script>

{#if $userStore?.id}
  <div class="flex gap-2 rounded place-items-center grow">
    <ProfilePic avatarClass="group" ringClass="w-8 h-8 text-xl" />
    <div class="input-group input-group-sm grow">
      <input
        type="text"
        class="input input-bordered input-primary grow input-sm"
        placeholder="Напишите свои мысли"
        bind:value="{value}"
      />
      <button
        class="h-full border-l-0 btn btn-primary hover:text-white btn-square btn-sm"
        on:click="{publishPost}"
      >
        <i class="fa-regular fa-paper-plane"></i>
      </button>
    </div>
  </div>
{/if}
