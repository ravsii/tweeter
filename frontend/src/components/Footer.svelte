<script lang="ts">
  import { url } from '@roxi/routify'
  import { onMount } from 'svelte'
  import PageCounter from './PageCounter.svelte'
  import ServerTime from './ServerTime.svelte'
</script>

<footer class="py-4 bg-white footer">
  <div class="flex main place-items-center">
    <div class="flex grow place-items-center gap-2">
      <i class="text-5xl fa-brands fa-square-twitter"></i>
      <p>Чирикер Inc.<br />Все права за 7 бригадой.</p>
    </div>
    <div class="grow">
      <span class="footer-title">Статистика:</span>
      <PageCounter />
      <ServerTime />
    </div>
  </div>
</footer>
