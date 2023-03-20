<script lang="ts">
  import { basePath } from '../../js/api'
  import { userStore } from '../../js/session'

  export let user = $userStore,
    avatarClass = '',
    ringClass = ''

  $: usePfp = user?.pfpUploaded
</script>

<div class:placeholder="{!usePfp}" class="avatar {avatarClass}">
  <div class="bg-neutral-focus text-neutral-content rounded-full {ringClass}">
    {#if usePfp}
      <img
        src="{basePath}/pfp/{user?.id}"
        alt="{user?.name}'s profile picture"
      />
    {:else}
      <span class="uppercase">{user?.name?.at(0)}</span>
    {/if}
  </div>
</div>
