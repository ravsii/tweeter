<script lang="ts">
  import { goto } from '@roxi/routify'
  import { notify } from '../../components/Notifications.svelte'
  import { userStore } from '../../js/session'

  if (!$userStore.mod) {
    $goto('/')
    notify('Атата', 'info')
  }
</script>

<slot />
