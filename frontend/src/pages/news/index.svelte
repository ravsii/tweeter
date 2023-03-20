<script lang="ts">
  import { onMount } from 'svelte'
  import NewsPost from '../../components/news/NewsPost.svelte'
  import { basePath } from '../../js/api'

  let news = []

  export async function loadNews() {
    const res = await fetch(basePath + '/news')
    const json = await res.json()

    if (json?.error) {
      return
    }

    news = json
  }

  onMount(loadNews)
</script>

<div class="flex flex-col gap-4 justify-items-center">
  {#each news ?? [] as post}
    <NewsPost
      post="{post}"
      on:publish="{loadNews}"
      on:like="{loadNews}"
      on:delete="{loadNews}"
    />
  {:else}
    <div
      class="p-4 text-xl font-light text-center rounded bg-base-100/20 text-base-content"
    >
      Ещё никто ничего сюда не написал, пока...
    </div>
  {/each}
</div>
