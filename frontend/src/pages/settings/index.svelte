<script lang="ts">
  import { notify } from '../../components/Notifications.svelte'
  import { basePath } from '../../js/api'

  async function handleUpload() {
    const el: any = document.getElementById('pfp')
    let photo = el.files[0]
    let formData = new FormData()

    formData.append('pfp', photo)
    const res = await fetch(basePath + '/users/uploadProfilePicture', {
      method: 'POST',
      body: formData,
      credentials: 'include',
    })
    const json = await res.json()

    if (json?.error) {
      notify(json.error, 'error')
      return
    }

    location.reload()
  }
</script>

<div class="flex flex-col p-4 rounded gap-4 bg-base-100">
  <div class="w-full form-control">
    <label class="label" for="pfp">
      <span class="label-text">Аватар:</span>
    </label>
    <input type="file" id="pfp" class="w-full file-input file-input-primary" />
  </div>

  <button on:click="{handleUpload}" class="btn btn-block btn-primary"
    >Обновить</button
  >
</div>
