<script>
  import AddNews from '../../components/admin/AddNews.svelte'
  import UserRoleList from '../../components/admin/UserRoleList.svelte'
</script>

<div class="flex flex-col gap-4">
  <div class="flex flex-col p-4 bg-white border rounded border-primary gap-4">
    <AddNews />
  </div>
  <div class="flex flex-col p-4 bg-white border rounded border-primary gap-4">
    <UserRoleList />
  </div>
</div>
