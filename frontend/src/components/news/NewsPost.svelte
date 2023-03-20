<script lang="ts">
  import { url } from '@roxi/routify'
  import dayjs from 'dayjs'
  import MarkdownIt from 'markdown-it'
  import { createEventDispatcher } from 'svelte'
  import { basePath } from '../../js/api'
  import { userStore } from '../../js/session'
  import { notify } from '../Notifications.svelte'
  import ProfilePic from '../user/ProfilePic.svelte'

  const format = 'HH:mm',
    dispatch = createEventDispatcher(),
    md = new MarkdownIt()

  interface Author {
    id: string
    name: string
  }

  interface Post {
    id: string
    content: string
    author: Author
    writtenAt: string
    likes: Array<Author>
  }

  export let post: Post | undefined = undefined
  let showReply = false

  async function handleLike() {
    if (!$userStore?.id) {
      notify('Залогиньтесь чтобы лайкать', 'info')
      return
    }

    const res = await fetch(basePath + '/news/' + post.id + '/like', {
      credentials: 'include',
    })
    const json = await res.json()

    if (json?.error) {
      notify(json.error, 'error')
      return
    }

    post = json
    dispatch('like')
  }

  $: likedByUsed = function (): boolean {
    if (!$userStore?.id) {
      return false
    }

    return post.likes.find((user) => user.id == $userStore.id) != null
  }
</script>

<div class="flex gap-4 p-4 bg-white rounded">
  <div class="flex flex-col gap-4 grow">
    <article class="prose">
      {@html post?.content}
    </article>
    <div class="flex justify-between gap-4 grow place-items-center">
      <div class="flex gap-2">
        <button
          class:btn-success="{likedByUsed()}"
          class="flex gap-2 place-items-center btn btn-sm group btn-outline"
          on:click="{handleLike}"
        >
          <div class="relative">
            <i class="absolute fa-solid fa-heart group-hover:animate-ping"></i>
            <i class="fa-solid fa-heart"></i>
          </div>
          {post?.likes?.length}
          Нравится
        </button>
      </div>
      <div>
        <div class="flex gap-4 place-items-center">
          <time class="text-xs opacity-70">
            {dayjs(post.writtenAt).format(format)}
          </time>
          <a
            href="{$url('/:user', { user: post.author.name })}"
            class="flex gap-2 place-items-center group"
          >
            <ProfilePic
              user="{post.author}"
              ringClass="w-6 h-6 group-hover:ring group-hover:ring-primary group-hover:ring-offset-2"
            />
            <span class="link link-hover link-primary ">
              {post.author.name}
            </span>
          </a>
        </div>
      </div>
    </div>
    {#if post?.likes?.length > 0}
      <div class="flex gap-2">
        <span>Понравилось:</span>
        {#each post.likes as user}
          <a
            href="{$url('/:user', { user: user.name })}"
            class="flex gap-2 place-items-center group"
          >
            <ProfilePic
              user="{user}"
              ringClass="w-6 h-6 group-hover:ring group-hover:ring-primary group-hover:ring-offset-1"
            />
            <span class="text-sm link link-hover link-primary">
              {user.name}
            </span>
          </a>
        {/each}
      </div>
    {/if}
  </div>
</div>
