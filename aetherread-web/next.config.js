/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  // Static export — hosted at beinganujchaudhary.web.app/projects/aetherRead
  output: 'export',
  basePath: '/projects/aetherRead',
  trailingSlash: true,   // generates index.html files instead of /route.html
  webpack: (config) => {
    // Required for pdfjs-dist worker
    config.resolve.alias.canvas = false;
    config.resolve.alias.encoding = false;
    return config;
  },
};

module.exports = nextConfig;
