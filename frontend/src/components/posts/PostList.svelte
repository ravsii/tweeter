<script lang="ts">
  import { createEventDispatcher, onMount } from 'svelte'
  import { basePath } from '../../js/api'
  import PostListItem from './PostListItem.svelte'

  type Posts = Array<any>

  let posts: Posts = []
  const dispatch = createEventDispatcher()

  export let user: any

  export async function loadPosts() {
    let res
    if ('user' in $$props) {
      res = await fetch(basePath + '/posts?user=' + user)
    } else {
      res = await fetch(basePath + '/posts')
    }
    const json = await res.json()

    if (json?.error) {
      return
    }

    posts = json
    dispatch('load', { posts: posts })
  }

  onMount(loadPosts)
</script>

<div class="flex flex-col gap-4 justify-items-center">
  {#each posts ?? [] as post}
    <PostListItem
      post="{post}"
      on:publish="{loadPosts}"
      on:like="{loadPosts}"
      on:delete="{loadPosts}"
    />
  {:else}
    <div
      class="p-4 text-xl font-light text-center rounded bg-base-100/20 text-base-content"
    >
      Ещё никто ничего сюда не написал, пока...
    </div>
  {/each}
</div>
