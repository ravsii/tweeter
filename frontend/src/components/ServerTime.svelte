<script lang="ts">
  import dayjs from 'dayjs'
  import { onMount } from 'svelte'
  import { basePath } from '../js/api'
  import { notify } from './Notifications.svelte'
  dayjs.locale('ru')

  let unixTime

  async function getServerTime() {
    const res = await fetch(basePath + '/time')

    if (!res.ok) {
      notify('Не могу загрузить серверное время', 'error')
      return
    }

    unixTime = parseInt(await res.text())
  }

  onMount(getServerTime)
</script>

<div>
  {#if unixTime}
    Серверное время: {dayjs.unix(unixTime).format('HH:mm:ss')}
    <span class="text-xs">
      ({unixTime})
    </span>
  {/if}
</div>
