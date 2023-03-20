<script>
  import Footer from '../components/Footer.svelte'
  import NavBar from '../components/NavBar.svelte'
  import Notifications from '../components/Notifications.svelte'
  import { updateSession } from '../js/session'

  $: updateSession()
</script>

<Notifications />

<div
  class="flex flex-col min-h-screen bg-white bg-gradient-to-tr from-primary to-secondary"
>
  <NavBar />
  <div class="flex flex-col my-4 main grow">
    <slot />
  </div>
  <Footer />
</div>
