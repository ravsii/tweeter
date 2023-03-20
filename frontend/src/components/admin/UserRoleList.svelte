<script lang="ts">
  import { createEventDispatcher, onMount } from 'svelte'
  import { basePath } from '../../js/api'
  import { userStore } from '../../js/session'
  import { notify } from '../Notifications.svelte'
  import ProfilePic from '../user/ProfilePic.svelte'

  let users
  const dispatch = createEventDispatcher()
  const roles = [
    {
      key: 'REGULAR',
      value: 'Обычный пользователь',
      disabled: false,
    },
    {
      key: 'MODERATOR',
      value: 'Модератор',
      disabled: !$userStore.admin,
    },
    {
      key: 'ADMIN',
      value: 'Администратор',
      disabled: !$userStore.superAdmin,
    },
    {
      key: 'SUPERADMIN',
      value: 'Главный администратор',
      disabled: true,
    },
  ]

  async function loadUsers() {
    const res = await fetch(basePath + '/users')
    const json = await res.json()

    if (json?.error) {
      notify(json.error, 'error')
      return
    }

    users = json
  }

  function handleRoleChange(user, el) {
    const value = el.value
    const text = el.options[el.selectedIndex].text
    return async function () {
      if (!value) {
        return
      }

      const body = JSON.stringify({ role: value })
      const res = await fetch(basePath + '/users/' + user.id + '/role', {
        method: 'POST',
        body: body,
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json',
        },
      })
      const json = await res.json()

      if (json?.error) {
        notify(json?.error, 'error')
        await loadUsers()
        return
      }

      notify(user.name + ' теперь ' + text, 'info')
      dispatch('change')
    }
  }

  onMount(loadUsers)
</script>

<div class="flex flex-col gap-1">
  <span class="text-lg font-bold">Изменить роли пользователей</span>
  <div class="m-0 divider"></div>
  <div class="w-full overflow-x-auto">
    <table class="table w-full table-compact">
      <!-- head -->
      <thead>
        <tr>
          <th>id</th>
          <th>Пользователь</th>
          <th>Роль</th>
        </tr>
      </thead>
      <tbody>
        {#each users ?? [] as user}
          <tr class="hover">
            <th class="text-xs">
              {user.id}
            </th>
            <td>
              <div class="flex items-center gap-4">
                <ProfilePic user="{user}" ringClass="w-8 h-8" />
                <div>
                  <div class="font-bold">{user.name}</div>
                  {#if user.pfpUploaded}
                    <div class="text-sm opacity-50">Красавчик с картинкой</div>
                  {/if}
                </div>
              </div>
            </td>
            <td>
              {#if $userStore.id == user.id}
                <span>Это вы</span>
              {:else}
                <select
                  class="w-full select select-bordered select-sm"
                  disabled="{$userStore.role == 'MODERATOR' ||
                    user.superAdmin ||
                    $userStore.role == user.role}"
                  value="{user.role}"
                  on:change="{handleRoleChange(user, this)}"
                >
                  {#each roles as role}
                    <option value="{role.key}" disabled="{role?.disabled}">
                      {role.value}
                    </option>
                  {/each}
                </select>
              {/if}
            </td>
          </tr>
        {/each}
      </tbody>
    </table>
  </div>
</div>
