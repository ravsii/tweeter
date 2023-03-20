<script lang="ts">
  import { url } from '@roxi/routify'
  import dayjs from 'dayjs'
  import { createEventDispatcher } from 'svelte'
  import { basePath } from '../../js/api'
  import { userStore } from '../../js/session'
  import { notify } from '../Notifications.svelte'
  import ProfilePic from '../user/ProfilePic.svelte'
  import RolePrefix from '../user/RolePrefix.svelte'
  import ReplyInput from './ReplyInput.svelte'

  const format = 'HH:mm',
    dispatch = createEventDispatcher()

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
    replyTo: Post
  }

  export let post: Post | undefined = undefined
  let showReply = false

  async function handleLike() {
    if (!$userStore?.id) {
      notify('Залогиньтесь чтобы лайкать', 'info')
      return
    }

    const res = await fetch(basePath + '/posts/' + post.id + '/like', {
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

  async function handleDelete() {
    if (!$userStore.mod) {
      notify('Вы не модератор', 'error')
      return
    }

    const res = await fetch(basePath + '/posts/' + post.id, {
      method: 'DELETE',
      credentials: 'include',
    })
    try {
      const json = await res.json()

      if (json && json?.error) {
        notify(json.error, 'error')
        return
      }
    } catch {
      // Ошибка появляется только при удачном удалении, поэтому игнорируем
    } finally {
      notify('Сообщение удалено', 'info')
      dispatch('delete')
    }
  }

  $: likedByUsed = function (): boolean {
    if (!$userStore?.id) {
      return false
    }

    return post.likes.find((user) => user.id == $userStore.id) != null
  }
</script>

<div class="flex p-4 bg-white rounded gap-4">
  <a href="{$url('/:user', { user: post?.author?.name })}">
    <ProfilePic
      user="{post.author}"
      avatarClass="group"
      ringClass="w-12 h-12 group-hover:ring group-hover:ring-primary group-hover:ring-offset-2 text-3xl"
    />
  </a>
  <div class="flex flex-col gap-4 grow">
    <div class="">
      <div class="flex gap-2 place-items-center">
        <a
          href="{$url('/:user', { user: post.author.name })}"
          class="flex gap-1 link link-hover link-primary place-items-center"
        >
          <RolePrefix class="text-sm" user="{post.author}" />
          {post.author.name}
        </a>
        <time class="text-xs opacity-70">
          {dayjs(post.writtenAt).format(format)}
        </time>
        {#if post?.replyTo}
          <i class="mx-4 fa-solid fa-arrow-right-long"></i>
          <time class="text-xs opacity-70">
            {dayjs(post.replyTo.writtenAt).format(format)}
          </time>
          <div class="flex min-w-0 text-sm gap-2 place-items-center">
            <a
              href="{$url('/:user', { user: post.replyTo.author.name })}"
              class="flex gap-1 link link-primary link-hover place-items-center"
            >
              <RolePrefix class="text-xs" user="{post.replyTo.author}" />
              {post.replyTo.author.name}
            </a>
            <span class="line-clamp-1">{post.replyTo.content}</span>
          </div>
        {/if}
      </div>
      <div class="">{post.content}</div>
    </div>
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
        {#if $userStore?.id}
          <button
            class="flex gap-2 place-items-center btn btn-sm group btn-outline"
            on:click="{() => {
              showReply = !showReply
            }}"
          >
            <div class="relative">
              <i class="absolute fa-regular fa-reply group-hover:animate-ping"
              ></i>
              <i class="fa-regular fa-reply"></i>
            </div>
            Ответить
          </button>
        {/if}
      </div>
      {#if $userStore.mod}
        <div class="flex gap-2 place-items-center">
          <button
            class="flex gap-2 place-items-center btn btn-xs btn-outline btn-error"
            on:click="{handleDelete}"
          >
            <i class="fa-solid fa-trash"></i>
            Удалить
          </button>
        </div>
      {/if}
    </div>
    {#if $userStore?.id && showReply}
      <div class="flex w-full">
        <ReplyInput
          parentPost="{post}"
          on:publish="{() => {
            showReply = false
          }}"
          on:publish
        />
      </div>
    {/if}
  </div>
</div>
