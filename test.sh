#!/bin/bash

config_file="config.txt"
GREEN='\033[0;32m'  # ANSI color code for green
RED='\033[0;31m'    # ANSI color code for red
NC='\033[0m'        # ANSI color code to reset color

# Read the previous user selection from the config file
if [ -e "$config_file" ]; then
    existing_options=$(cat "$config_file")
else
    existing_options=""
fi

# Define dynamic options with corresponding names and URLs
options=(
    "csm=https://csm.example.com"
    "Infra=https://infra.example.com"
    "producer=https://producer.example.com"
)

# Display the names to the user with option numbers, including a message for existing options
function display_menu {
    echo "Select an option:"
    index=1
    for option in "${options[@]}"; do
        name=${option%=*}
        if grep -q "$name" <<< "$existing_options"; then
            echo -e "${index}. ${name} ${GREEN}-- You already have this${NC}"
        else
            echo "${index}. $name"
        fi
        index=$((index + 1))
    done
}

# Get user input
while true; do
    display_menu
    read -p "Enter your choice(s) separated by commas: " user_choices

    # Save the user's selection to the config file
    IFS=',' read -ra choices_array <<< "$user_choices"
    invalid_choice=false

    for choice in "${choices_array[@]}"; do
        selected_option=${options[$((choice - 1))]}
        selected_name=${selected_option%=*}

        if grep -q "$selected_name" <<< "$existing_options"; then
            echo -e "${RED}You selected an option for a feature that already exists. Please try again!${NC}"
            invalid_choice=true
            break
        fi

        existing_options+="$selected_name"$'\n'
    done

    if [ "$invalid_choice" = false ]; then
        break
    fi
done

# Write the selected options back to the config file
echo -e "$existing_options" > "$config_file"

# Process the user's choices dynamically
for choice in "${choices_array[@]}"; do
    selected_option=${options[$((choice - 1))]}
    url=${selected_option#*=}
    # Call update.sh with arguments for each selected option
    ./update.sh "$choice" "$url"
done





#!/bin/bash

# Declare options array
options=(
    "Infra=$infra_br"
    "Deploy=$deploy_br"
    "Producer=$pr_br"
    "Consumer=$cs_br"
    "Csm=$csm_br"
)

# Function to process and invoke ./update.sh
process_option() {
    local option="$1"

    # Iterate through options array
    for opt in "${options[@]}"; do
        # Extract option and corresponding value
        opt_name=$(echo "$opt" | cut -d '=' -f 1)
        opt_value=$(echo "$opt" | cut -d '=' -f 2)

        # If the option matches, invoke ./update.sh
        if [ "$option" == "$opt_name" ]; then
            echo "./update.sh $option $opt_value"
            # Uncomment the line below to actually invoke ./update.sh
            # ./update.sh "$option" "$opt_value"
        fi
    done
}

# Read the file with delimited text
input_file="config.txt"

while IFS='|' read -r -a options_array; do
    # Process each option in the array
    for option in "${options_array[@]}"; do
        process_option "$option"
    done
done < "$input_file"

