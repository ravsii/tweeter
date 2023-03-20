<script lang="ts">
  import { goto } from '@roxi/routify'
  import { onMount } from 'svelte'
  import { notify } from '../../components/Notifications.svelte'
  import { basePath } from '../../js/api'
  import { logout } from '../../js/session'

  async function logoutRequest() {
    await fetch(basePath + '/user/logout', {
      method: 'GET',
      credentials: 'include',
    })

    logout()
    notify('Вы вышли', 'info')
    $goto('/')
  }

  onMount(logoutRequest)
</script>
