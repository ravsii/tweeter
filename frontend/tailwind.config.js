/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{svelte,js,ts}'],
  theme: {
    extend: {},
  },
  purge: ['./src/**/*.svelte', './src/**/*.css'],
  plugins: [
    require('daisyui'),
    require('@tailwindcss/line-clamp'),
    require('@tailwindcss/typography'),
  ],
  daisyui: {
    themes: ['corporate'],
  },
}
