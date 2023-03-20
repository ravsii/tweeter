<script context="module" lang="ts">
  import { writable } from 'svelte/store'

  interface Toast {
    id: number
    msg: string
    iconClass: string
    class: string
    timer: any
  }

  type ToastStore = Array<Toast>

  export const messagesStore = writable<ToastStore>([])

  export const notify = (message: string, type: string, timeout?: number) => {
    let iconClass
    switch (type) {
      case 'success':
        iconClass = 'fa-regular fa-circle-check'
        break
      default:
      case 'info':
        iconClass = 'fa-regular fa-circle-info'
        break
      case 'warning':
        iconClass = 'fa-regular fa-triangle-exclamation'
        break
      case 'error':
        iconClass = 'fa-regular fa-circle-exclamation'
        break
    }

    // Adding new toast
    const newToast: Toast = {
      id: Math.random(),
      msg: message,
      iconClass: iconClass,
      class: type,
      timer: setTimeout(
        () =>
          messagesStore.update((v) => {
            v.shift()
            return v
          }),
        timeout ?? 2000
      ),
    }
    messagesStore.update((array) => (array = [...array, newToast]))
  }
</script>

<script lang="ts">
  import inject from 'svelte-inject'

  let messages
  messagesStore.subscribe((v) => {
    messages = v
  })

  const close = (msgObject) => {
    clearInterval(msgObject.timer)
    messagesStore.update((messages) =>
      messages.filter((m) => m.id !== msgObject.id)
    )
  }
</script>

{#if messages?.length > 0}
  <div use:inject>
    <div
      class="fixed toast toast-top toast-end opacity-90 place-items-end"
      style="z-index:1000"
    >
      {#each messages as m}
        <div
          class:alert-success="{m.class === 'success'}"
          class:alert-info="{m.class === 'info'}"
          class:alert-warning="{m.class === 'warning'}"
          class:alert-error="{m.class === 'error'}"
          class="flex p-4 shadow-md cursor-pointer rounded-md alert lg:max-w-md w-fit shadow-gray-500/30 flex-nowrap"
          on:click="{() => close(m)}"
        >
          <div class="flex w-full gap-2 place-items-center">
            <i class="{m.iconClass}"></i>
            <span>{m.msg}</span>
          </div>
        </div>
      {/each}
    </div>
  </div>
{/if}
