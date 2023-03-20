<script lang="ts">
  import { url } from '@roxi/routify'
  import PageCounter from '../../components/PageCounter.svelte'

  let errorMsg = ''

  function error(message: string) {
    errorMsg = message
  }

  function reset() {
    errorMsg = ''
  }
</script>

<div
  class="flex flex-col min-h-screen bg-white bg-gradient-to-tr from-primary/50 to-secondary/50"
>
  <div class="flex justify-center main grow place-items-center">
    <div class="flex flex-col w-full max-w-sm gap-8">
      <a
        class="flex mx-auto text-xl normal-case gap-2 place-items-center"
        href="{$url('/')}"
      >
        <i class="text-2xl fa-brands fa-square-twitter"></i>
        Чирикер
      </a>
      <div class="p-4 border rounded glass">
        {#if errorMsg}
          <div
            class="alert alert-error shadow-lg mb-4 flex justify-between !flex-row"
          >
            <div>
              <i class="fa-solid fa-circle-exclamation"></i>
              <span>{errorMsg}</span>
            </div>
            <button on:click="{reset}" class="btn btn-sm btn-circle btn-ghost">
              <i class="cursor-pointer fa-solid fa-xmark"></i>
            </button>
          </div>
        {/if}
        <slot scoped="{{ error }}" />
      </div>
      <PageCounter />
    </div>
  </div>
</div>
