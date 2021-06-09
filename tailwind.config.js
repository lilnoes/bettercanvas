module.exports = {
  purge: ["./web/**/*html"],
  mode: "jit",
  darkMode: false, // or 'media' or 'class'
  theme: {
    extend: {
      keyframes: {mine: {
        "0%": {opacity: 0},
        "100%": {opacity: 1},
      }},
      animation: {
        mine: "mine 1s ease-in 1"
      }
    },

  },
  variants: {
    extend: {},
  },
  plugins: [],
  corePlugins: {
    preflight: false
  }
}
