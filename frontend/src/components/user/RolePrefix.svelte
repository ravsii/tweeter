<script lang="ts">
  export let user
  let class_
  export { class_ as class }
</script>

{#if user?.superAdmin}
  <span class="text-red-700 {class_}"> (S) </span>
{:else if user?.admin}
  <span class="text-rose-400 {class_}"> (A) </span>
{:else if user?.mod}
  <span class="text-emerald-500 {class_}">(M)</span>
{/if}
