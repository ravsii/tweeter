<script lang="ts">
  import { goto, url } from '@roxi/routify'
  import { onMount } from 'svelte'
  import { basePath } from '../../js/api'
  import { notify } from '../Notifications.svelte'
  import ProfilePic from './ProfilePic.svelte'
  import RolePrefix from './RolePrefix.svelte'

  export let user, posts

  let userInfo

  async function loadUserInfo() {
    const res = await fetch(basePath + '/users/' + user)
    const json = await res.json()

    if (json?.error) {
      notify(json.error, 'error')
      $goto('/')
      return
    }

    userInfo = json
  }

  $: calculateLikes = function (posts) {
    if (!posts || posts?.length == 0) {
      return 0
    }

    return posts.reduce((a, p) => a + p.likes?.length, 0)
  }

  onMount(loadUserInfo)
</script>

<a
  href="{$url('')}"
  class="flex my-8 rounded rounded-l-full gap-8 border-primary bg-base-100 place-items-center"
>
  <ProfilePic
    user="{userInfo}"
    avatarClass="-my-8"
    ringClass="w-36 h-36 ring ring-primary ring-offset-2 text-8xl"
  />
  <div
    class="flex text-5xl gap-2 text-base-content font-extralight grow place-items-center"
  >
    <RolePrefix class="text-3xl" user="{userInfo}" />
    {userInfo?.name}
  </div>
  <div class="text-lg text-base-content grow">
    {#if posts}
      <div>Постов: {posts?.length}</div>
      <div>Лайков получено: {calculateLikes(posts)}</div>
    {/if}
  </div>
</a>
