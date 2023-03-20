<script lang="ts">
  import MarkdownIt from 'markdown-it'
  import { createEventDispatcher } from 'svelte'
  import { basePath } from '../../js/api'
  import { notify } from '../Notifications.svelte'

  const md = new MarkdownIt(),
    dispatch = createEventDispatcher()

  let value = ''

  async function publishPost() {
    if (!value) {
      return
    }

    const body = JSON.stringify({ content: result })
    const res = await fetch(basePath + '/news', {
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

    notify('Новость опубликована', 'success')
    dispatch('publish')
    value = null
  }

  $: result = value ? md.render(value) : ''
</script>

<div class="flex flex-col gap-2">
  <span class="text-lg font-bold">Добавить новость</span>
  <div class="m-0 divider"></div>
  <div class="flex flex-col gap-4">
    <div class="flex gap-2">
      <div class="flex flex-col flex-1 gap-2">
        <div class="flex gap-4 place-items-center">
          <span>Текст новости</span>
          <span class="flex gap-2 text-sm opacity-50 place-items-center">
            <i class="fa-brands fa-markdown"></i>
            Markdown
          </span>
        </div>
        <textarea
          placeholder="Новость"
          class="textarea textarea-bordered textarea-primary"
          rows="10"
          bind:value="{value}"></textarea>
      </div>
      <div class="flex flex-col flex-1 gap-2">
        <span>Превью</span>
        <article class="p-2 prose-sm border border-primary grow">
          {@html result}
        </article>
      </div>
    </div>
    <div class="flex justify-end grow">
      <button class="text-white btn btn-primary" on:click="{publishPost}">
        Опубликовать
      </button>
    </div>
  </div>
</div>
