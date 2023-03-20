<script lang="ts">
  import { afterPageLoad } from '@roxi/routify'
  import { onMount } from 'svelte'
  import { basePath } from '../js/api'
  import { notify } from './Notifications.svelte'

  interface Counter {
    counter: number
    url: string
  }

  let count: Counter
  let currentPath = window.location.pathname

  async function loadPageCount(): Promise<Counter> {
    const res = await fetch(basePath + '/count?URL=' + currentPath)
    const json = await res.json()

    if (!json?.counter) {
      notify('Не могу загрузить счётчик страниц', 'error')
      return
    }

    count = json
  }

  async function updateCounter() {
    if (window.location.pathname != currentPath) {
      currentPath = window.location.pathname
      await loadPageCount()
    }
  }

  onMount(() => {
    loadPageCount()
  })

  $afterPageLoad((): boolean => {
    updateCounter()
    return
  })
</script>

<div>
  {#if count}
    Счётчик: {count.counter}
    <span class="ml-2 text-xs">
      Страница: <kbd class="kbd kbd-xs">{count.url}</kbd>
    </span>
  {/if}
</div>
