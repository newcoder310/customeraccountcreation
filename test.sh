#!/bin/bash

config_file="config.txt"

# Read the previous user selection from the config file
if [ -e "$config_file" ]; then
    selected_options=$(cat "$config_file")
else
    selected_options=""
fi

# Define dynamic options with corresponding names and URLs
options=(
    ["1"]="csm=https://csm.example.com"
    ["2"]="Infra=https://infra.example.com"
    ["3"]="producer=https://producer.example.com"
)

# Filter out options that are already present in config.txt
for selected_option in $(echo "$selected_options" | tr ',' ' '); do
    unset "options[$selected_option]"
done

# Display only the names to the user
echo "Select an option:"
for i in "${!options[@]}"; do
    IFS='=' read -ra option_parts <<< "${options[$i]}"
    name=${option_parts[0]}
    echo "$i. $name"
done

# Get user input
read -p "Enter your choice(s) separated by commas: " user_choices

# Save the user's selection to the config file
echo "$selected_options,$user_choices" > "$config_file"

# Process the user's choices dynamically
IFS=',' read -ra choices_array <<< "$user_choices"

for choice in "${choices_array[@]}"; do
    selected_option=${options[$choice]}
    if [ -n "$selected_option" ]; then
        IFS='=' read -ra option_parts <<< "$selected_option"
        name=${option_parts[0]}
        url=${option_parts[1]}
        # Call update.sh with arguments for each selected option
        ./update.sh "$name" "$url"
    else
        echo "Invalid choice: $choice"
    fi
done
