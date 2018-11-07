# NewsApp
This app fetches news from the guardian API in a JSON format. the result is displayed in 3 categories "Sports, Politics and Technology.
A viewpager and adapter display 3 fragment for the respective categories.an AsyncLoaderTask performs the Http request to get the JSON string
as an InputStream the stream is read using a BufferedReader, then parsed and saved in an array list with D parameter News type.
