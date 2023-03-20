<script>
  import { isActive, url } from '@roxi/routify'
  import { userStore } from '../js/session'
  import ProfilePic from './user/ProfilePic.svelte'
</script>

<div class="sticky top-0 z-10 shadow-lg bg-base-100 shadow-black/30">
  <div class="px-0 navbar main">
    <div class="flex-1 gap-2">
      <a class="text-xl normal-case btn btn-ghost gap-2" href="{$url('/')}">
        <i class="text-2xl fa-brands fa-square-twitter"></i>
        Чирикер
      </a>
      <a
        class="btn btn-ghost"
        class:text-primary="{$isActive('/news')}"
        href="{$url('/news')}"
      >
        Новости
      </a>
    </div>
    <div class="flex gap-2">
      {#if $userStore?.id}
        <div class="flex gap-2 place-items-center">
          {#if $userStore.mod}
            <a
              href="{$url('/admin')}"
              class="btn btn-ghost rounded-btn gap-2 btn-square"
            >
              <i class="fa-solid fa-eye"></i>
            </a>
          {/if}
          <div class="dropdown dropdown-end dropdown-bottom !text-left">
            <label
              tabindex="0"
              class="btn btn-ghost rounded-btn gap-2 group"
              for="#"
            >
              <ProfilePic user="{$userStore}" ringClass="w-6 h-6" />
              {$userStore.name}
            </label>
            <ul
              tabindex="0"
              class="p-2 mt-2 border shadow menu dropdown-content bg-base-100 rounded-box w-52 border-primary"
            >
              <li>
                <a href="{$url('/' + $userStore.name)}" class="flex gap-2">
                  <i class="fa-regular fa-user"></i>
                  Профиль
                </a>
              </li>
              <li>
                <a href="{$url('/settings')}" class="flex gap-2">
                  <i class="fa-regular fa-gear"></i>
                  Настройки
                </a>
              </li>
              <li>
                <a href="{$url('/auth/logout')}" class="flex gap-2">
                  <i class="fa-solid fa-right-from-bracket"></i>
                  Выйти
                </a>
              </li>
            </ul>
          </div>
        </div>
      {:else}
        <a class="btn btn-ghost" href="{$url('/auth/login')}"> Войти </a>
        <a class="btn btn-ghost" href="{$url('/auth/register')}">
          Регистрация
        </a>
      {/if}
    </div>
  </div>
</div>
