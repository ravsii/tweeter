<script>
  import PostInput from '../components/posts/PostInput.svelte'
  import PostList from '../components/posts/PostList.svelte'

  let loadPosts
</script>

<div class="flex flex-col grow gap-4">
  <PostInput on:publish="{loadPosts}" />
  <PostList bind:loadPosts="{loadPosts}" />
</div>
