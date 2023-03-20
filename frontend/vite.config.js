import { svelte } from '@sveltejs/vite-plugin-svelte'
import autoPreprocess, { typescript } from 'svelte-preprocess'
import { defineConfig } from 'vite'

const production = false

export default defineConfig({
  server: {
    port: 5000,
  },
  plugins: [
    svelte({
      preprocess: autoPreprocess(),
    }),
    typescript({ sourceMap: !production }),
  ],
})
