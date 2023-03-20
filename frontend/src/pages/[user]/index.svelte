<script lang="ts">
  import PostList from '../../components/posts/PostList.svelte'
  import UserInfo from '../../components/user/UserInfo.svelte'

  export let user
  let posts

  function handleLoad(event) {
    posts = event?.detail?.posts
  }
</script>

<UserInfo user="{user}" posts="{posts}" />
<PostList user="{user}" on:load="{handleLoad}" />
